#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import rospy
import roslib
import actionlib
import smach
import smach_ros
import time
import roslaunch

from std_msgs.msg import String, Float64, Bool
from sensor_msgs.msg import LaserScan
from happymimi_navigation.srv import NaviLocation
from happymimi_msgs.srv import StrTrg, SetStr
from happymimi_voice_msgs.srv import TextToSpeech, YesNo

base_path = roslib.packages.get_pkg_dir('happymimi_teleop') + '/src/'
sys.path.insert(0, base_path)
from base_control import BaseControl

class HomeAccident ():
    def __init__(self):
        self.yesno_srv = rospy.ServiceProxy('/yes_no', YesNo)
        self.tts_publisher = rospy.Publisher('/mimic1/tts/topic', String, queue_size=10)
        self.tts_service = rospy.ServiceProxy('/mimic1/tts/service', String)
        self.base_control = BaseControl()
        self.navi_srv = rospy.ServiceProxy("/navi_location_server", NaviLocation)
        self.head = rospy.Publisher('/servo/head',Float64,queue_size=10)

        rospy.Subscriber("/accident/sign", String, self.execute)
        emergency_sign = rospy.Publisher("/emergency/sigh", String, queue_size=10)

        self.navi_count = 0

    def tts_pub(message):
        tts_publisher.publish(message)
        rospy.sleep(1.0)

    def tts_srv(message):
        tts_service(message)

    def emergency_sign():
        emergency_sign.publish("Need Help!")

    def execute(self):
        tts_pub("Dont Worry. I comming")

        while self.navi_count < 3: #Navigationに失敗しても最低3回はやり直す
            result = self.navi_srv('start_1_cml').result #ここでNavigationの位置を挿入
            if result:
                self.navi_count = 0
                break
            self.count += 1
        rospy.sleep(0.5)

        tts_srv("Are you ok")
        answer = self.yesno_srv().result

        if answer:
            tts_pub("I'm glad to hear that you're okay.")
            return "No Accident"
        
        else:
            tts_pub("I'm sorry to hear that. Please hold on while I contact emergency services.")
            rospy.sleep(0.5)
            self.head.publish(25)
            rospy.sleep(0.5)
            emergency_sign()
            return "An Accident Occurred"

if __name__ == '__main__':
    rospy.init_node('home_accident')
    rospy.loginfo("HOME_ACCIDENT_NODE is Available")
    rate = rospy.Rate(1)
    HomeAccidentClass = HomeAccident()





        
        
