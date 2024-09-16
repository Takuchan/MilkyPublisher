cmake_minimum_required(VERSION 3.22.1)

project("ROS2Connection")

set(CMAKE_CXX_STANDARD 17)

find_package(fastdds-prefab)
file(GLOB_RECURSE FASTDDS_GEN "*.cxx")

add_library(${CMAKE_PROJECT_NAME} SHARED
        publisher.cpp
        ${FASTDDS_GEN})

target_include_directories(${CMAKE_PROJECT_NAME} PUBLIC .)

target_link_libraries(${CMAKE_PROJECT_NAME}
        fastdds-prefab::fastrtps
        fastdds-prefab::fastcdr)