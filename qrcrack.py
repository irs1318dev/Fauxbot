import qrcode

def generate_qr_to_console(data):
    qr = qrcode.QRCode(
        version=2,
        box_size=1,  # Use a small box_size for console output
        border=1,
    )
    qr.add_data(data)
    qr.make(fit=True)
    console_qr = qr.print_ascii(tty=True) 
    print(console_qr)

if False:
    import time, os
    print('started')
    for i in range(999):
        code = "kao-kevin-2029-" + str(i).zfill(3)
        print(code)
        generate_qr_to_console(code)
        time.sleep(5)
        os.system('cls')
else:
    generate_qr_to_console("kao-kevin-2029-301")
