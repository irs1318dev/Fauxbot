# Setting up your Environment

To prepare your computer for Robot programming with our team, you will need to follow the following steps:
1. Installing everything:
   1. Install development environment.  To install the latest "Long-Term Support" JDK (21, 25, ...), download the correct installer for your operating system from [Oracle's JDK page](https://www.oracle.com/java/technologies/downloads/).  You will then need to configure the JAVA_HOME environment variable, in "Configuraing things" step 2 below.
   2. Install regular VS Code.  Run the [VS Code Installer](https://code.visualstudio.com/) to install the regular version of VS Code.  Be sure to select the version appropriate for your operating system.
   3. Install Git.  Run the [Git installer](https://git-scm.com/downloads) to install the Git client.  Be sure to select the version appropriate for your operating system.  During installation make sure to change the option to make "Visual Studio Code" the default editor used by git.  If not, you will need to make sure to follow step 2.1.1 below.
   4. Install the Java Extension Pack for VS Code.  In VS Code, open the extensions side bar by either clicking on the corresponding icon or clicking View --> Open View..., typing "Extensions", and selecting "Extensions" (side bar).  Within the Extensions side bar, search for the "Java Extension Pack" published by Microsoft, and then click to install it.  Optionally, I would also recommend installing the "Live Share Extension Pack" published by Microsoft.
   5. Install GitHub Desktop (optional).  Our team uses GitHub as the host for our source control system, so if you are more comfortable having a GUI for interacting with it, then GitHub Desktop will be the best supported.  Install the appropriate version of [GitHub Desktop](https://desktop.github.com/) for your operating system.
2. Configuring things:
   1. Git uses VIM as the default text editor for commit messages.  Normal people not very familiar with VIM usage, so it is strongly recommended to change to a more normal windowed application as VIM can be very confusing for anyone without VIM experience.  I would recommend switching to use VS Code as your editor and default diff tool.
      1. If you didn't successfully configure git to use VS Code as the default text editor, run ```git config --global core.editor "code --wait"``` from a Command Prompt window.
         1. If you are on a non-Windows system, you may need to make sure that running the command "code" from the command line (Terminal) successfully opens VS Code.  If it does not, Open VS Code, then open its command window (CTRL+P on Linux, COMMAND+P on Mac??), type "> path" into the window that appears, and select the option that mentions updating "path" or installing the "code" command for opening VS Code.
      2. Modify your Global settings by running ```git config --global -e```, and then adding the following entries to the end of the file:
      ```
      [diff]
        tool = vscode
      [difftool "vscode"]
        cmd = code --wait --diff \"$LOCAL\" \"$REMOTE\"
      ```
   2. VS Code's Java extension sometimes needs extra hints to find where the Java JDK was installed.  To do this, you will need to add an environment variable on Windows (sorry, don't know what to do for Mac).
      1. In Windows 11, press start and type "environment" in the search bar.
      2. Click the option that says "Edit the system environment variables".  
      3. Click the "Environment Variables..." button at the bottom of the window.
      4. Within the "System variables" section, click the "New..." button.
      5. In the "New System Variable" dialog, use the Variable name "JAVA_HOME" and value "C:\Program Files\Java\jdk-21" (or later if you installed a different version of the JDK), then click Ok.
      6. Click ok to close the Environment Variables and System Properties windows.
      7. Restart your computer.
3. Get the code onto your local machine.
   1. Copy the repository's URL.  In GitHub, find the repository you are interested in, click the "Clone or download" button, and then copy the text (e.g. "https://github.com/irs1318dev/Fauxbot.git").
   2. Using commandline:
      1. Open a commandline window.  On Windows, search for "cmd" or "Command Prompt".  Navigate within your directory structure to a directory where you'd like to keep your source files (e.g. "```cd C:\Users\username\git\```").
      2. Run the following git command to clone the repository to your local machine: "```git clone https://github.com/irs1318dev/Fauxbot.git```"
      3. Once the repository has been cloned, navigate into the main directory (e.g. "```cd C:\Users\username\git\Fauxbot```") and tell Gradle to build the code in the directory (type "```gradlew build```").  If gradle hasn't been installed yet, this should trigger it to be installed.  If you are running MacOS or Linux, instead run gradle wrapper with a dot and forward slash in front of it ("```./gradlew```").  If you are running in PowerShell instead of Command Prompt, instead run gradle wrapper with a dot and a backslash in front of it ("```.\gradlew```").
      4. Open VS Code for this project.  In the main directory, type "```code irs1318_Fauxbot.code-workspace```".  This will tell VS Code to open with a reference to the folder you are currently exploring within cmd.
    3. Using GitHub Desktop:
       1. Open GitHub Desktop.  For the best experience, you will need a GitHub user account that has been added to the irs1318dev group.  If you haven't done that, consider doing that first.
       2. Go to File --> Clone Repository.  If you have been added to the irs1318dev group, you can select the repository you want (e.g. "irs1318dev/Fauxbot") from a list of repositories under the GitHub.com tab.  Otherwise, go to the the URL tab and enter the repository you want (e.g. "irs1318dev/Fauxbot") in the text box.  Then choose a local path where this repository will be cloned (e.g. "C:\Users\username\git\Fauxbot") and click the clone button.
       3. Open VS Code for this project.  Open VS Code and open the folder where code is located by going to File --> Open Folder, and selecting the folder within the one where the repository was cloned (e.g. "C:\Users\username\git\Fauxbot").

If you have issues building the code using gradle for the first time, it may be one of the following issues:
1. Insufficient disk space.  If you get a message talking about not being able to copy a file or create a directory, it may be a disk space issue.  Please clear some space so you have enough to build.
2. Insufficient permissions to run gradlew.  On Mac/Linux, gradlew is blocked from running by default.  To allow it, run "```chmod +x gradlew```" and then run "```chmod 755 gradlew```".
3. Forgetting slashes/etc.  On Mac/Linux or in a Powershell window, you may need to run gradlew in a special way.  On Mac/Linux, you may need to run gradlew as "```./gradlew```".  In Powershell, you may need to run gradlew as "```.\gradlew```".

If you have issues using VS Code where everything is underlined in Red, it may be one of the following issues:
1. Opened the folder instead of the workspace.  Sometimes students open the folder containing the repository in VS Code instead of opening the Workspace file (```*.code-workspace```).  Upon opening the directory, you may see a pop-up in the bottom-right hand of your VS Code window asking if you meant to open the workspace instead - that's the easiest way to switch.  The workspace tends to more reliably than opening the folder itself.  To open the workspace, you can go to File --> Open Workspace from File..., and select the code-workspace file under the root of the repository.
2. Java Extension misconfigured.  It's possible that
3. Repository was created under shared drive folder (e.g. OneDrive).  Above, it was suggested that the repository get cloned under ```C:\Users\username\git\repository_name```, but some tools like GitHub Desktop may suggest creating the repository in a subfolder under your Documents folder, e.g. ```C:\Users\username\OneDrive\Documents\GitHub\repository_name```. You will run into fewer problems if you remove that repository and re-clone to the better location.
