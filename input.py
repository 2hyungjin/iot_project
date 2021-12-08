import requests
import serial

url = "http://192.168.0.38/message"

def sendmessage(message):
    message = {'type' : message}
    requests.post(url, json = message)



if __name__ == "__main__":
    ser= serial.Serial('/dev/ttyACM0',9600, timeout=1)
    ser.flush()
    while True:
        if ser.in_waiting > 0:
            line = ser.readline().decode('utf-8').rstrip()
            print(line)
            sendmessage(line)