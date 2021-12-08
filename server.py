from flask import Flask
from pyfcm import FCMNotification
import serial
from flask import request
KEY = ""
TOKEN=""

#pusth_service = FCMNotification(APIKEY)

app = Flask(__name__)

ser=serial.Serial('/dev/ttyACM0',9600,timeout=1)

@app.route("/")
def aa():
    return "aa"

@app.route("/led/on")
def funLedOn():
    ser.write('1'.encode())
    return "Led On"

@app.route("/led/off")
def funLedOff():
    ser.write('0'.encode())
    return "Led Off"

@app.route("/token", methods=['post'])
def gettoken():
    global TOKEN
    param=request.get_json()
    print(param)
    TOKEN=str(param)
    return "token"

@app.route("/key", methods=['post'])
def getkey():
    global KEY
    param=request.get_json()
    print(param)
    KEY=str(param)
    return "KEY"

@app.route("/message", methods=['post'])
def sendMessage():
    type =  request.get_json()['type']
    print(type)
    if(type == "gas"):
        sendGasFcm()
    if(type == "pir"):
        sendPirFcm()
    return "message"

def sendFcm(message):
    global KEY
    push_service=FCMNotification(KEY)
    push_service.single_device_data_message(registration_id=TOKEN, data_message=message)

def sendGasFcm():
    message = {'title' : "가스 누출",'body' : "iot"}
    sendFcm(message)

def sendPirFcm():
    message = {'title' : "침입 감지",'body' : "iot"}
    sendFcm(message)

if __name__ == "__main__":
    app.run(host='0.0.0.0',port=80,debug=True)