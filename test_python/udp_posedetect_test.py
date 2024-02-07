"""
このプログラムは、UDP通信で取得したPoseDetectのデータの欠損値を測るテストコードです。

検証項目
・欠損値の発生頻度
・UDP通信の平均取得時間
・１フレームの骨格にかかる取得時間

このコードの使い方
KotlinのUDP Contorollerのプログラムで以下のように変更する
val sendData: String = "$landmarkName"
"""

import socket

# 受信するIPアドレスとポート番号を設定
UDP_IP = "0.0.0.0"
UDP_PORT = 4000

# UDPソケットを作成
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# ソケットにIPアドレスとポート番号をバインド
sock.bind((UDP_IP, UDP_PORT))

# 骨格検知のデータを格納するリスト
datas = [] # １フレームのデータを格納するリスト
data_tmp = [] # datasに格納する前のデータを格納するリスト
# 欠損値が出た回数
missing_val = 0

# count
count = 0

while True:
    # データを受信
    data, addr = sock.recvfrom(1024)
    # print(f"received message: {data.decode()} from {addr}")
    get_data = int(data.decode())
    count += 1

    if count > 33:
        missing_val += 1
        count = 0
        datas.append(data_tmp)
        print("32以上欠損",data_tmp)
        data_tmp = []
        
    else:
        if (get_data >= 0 & get_data <= 32):
            data_tmp.append(get_data)
            if (get_data == 32):  # PoseDetectの最後の数字までUDP取得できた場合
                datas.append(data_tmp)   
                if (len(data_tmp) == count & count == 33):    # 欠損値がない場合
                    print("成功", len(data_tmp))
                    data_tmp = []
                    count = 0
                else:
                    print("欠損: ", len(data_tmp))
                    data_tmp = []
                    missing_val += 1
                    count = 0 
                
    
    