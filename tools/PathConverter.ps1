param(
    [String]$Path,
    [String]$OutputDir
)

if ([String]::IsNullOrEmpty($Path))
{
    $Path = Convert-Path .
}

if ([String]::IsNullOrEmpty($OutputDir))
{
    $OutputDir = Covnert-Path .
    $OutputDir += "\Result"
    if (-not $(Test-Path $OutputDir))
    {
        New-Item -ItemType directory -Path $OutputDir
    }
}

if (-not $(Test-Path $Path))
{
    throw "Path $Path doesn't exist"
}

if (-not $(Test-Path $OutputDir))
{
    throw "Path $OutputDir doesn't exist"
}

$files = Get-ChildItem $Path | ?{ $_.Extension -eq ".csv" }
$names = $files | %{ $_.Name.Split(".")[0] } | group -NoElement | ?{ $_.Count -eq 3 } | %{ $_.Name }

$names | %{
    $name = $_
    $leftCsv = Import-Csv $(Join-Path $Path -ChildPath $($name + ".right.pf1.csv"))
    $rightCsv = Import-Csv $(Join-Path $Path -ChildPath $($name +  ".left.pf1.csv"))

    if ($leftCsv.Count -ne $rightCsv.Count)
    {
        throw "Mismatched counts between right and left files ($name)"
    }

    $firstHeading = $null;
    $result = @()
    0..$($leftCsv.Count) | %{
        $leftRow = $leftCsv[$_]
        $rightRow = $rightCsv[$_] 
        if (-not [String]::IsNullOrEmpty($rightRow.position) -and -not [String]::IsNullOrEmpty($rightRow.velocity))
        {
            $heading = [double]::Parse($leftRow.heading)
            if ($firstHeading -eq $null)
            {
                $firstHeading = $heading
            }

            $heading = ($heading - $firstHeading + 4*[Math]::PI) % (2 * [Math]::PI)
            $row = New-Object -TypeName PSCustomObject
            $row | Add-Member -NotePropertyName LeftPosition -NotePropertyValue $($leftRow.position)
            $row | Add-Member -NotePropertyName RightPosition -NotePropertyValue $($rightRow.position)
            $row | Add-Member -NotePropertyName LeftVelocity -NotePropertyValue $($leftRow.velocity)
            $row | Add-Member -NotePropertyName RightVelocity -NotePropertyValue $($rightRow.velocity)
            $row | Add-Member -NotePropertyName Heading -NotePropertyValue $($heading)
            $result += $row
        }
    }

    $result | Export-Csv -Path $(Join-Path $OutputDir -ChildPath $($name + ".csv")) -NoTypeInformation
}
