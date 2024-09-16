cmake_minimum_required(VERSION 3.22.1)
project(YourProjectName)

# Fast-DDSのビルド設定
include(ExternalProject)

# foonathan_memory_vendor
ExternalProject_Add(foonathan_memory_vendor
        GIT_REPOSITORY  https://github.com/eProsima/foonathan_memory_vendor.git
        GIT_TAG         v1.3.1
        CMAKE_ARGS
        -DCMAKE_INSTALL_PREFIX=${CMAKE_CURRENT_BINARY_DIR}/install
        -DBUILD_SHARED_LIBS=OFF
)

# Fast-CDR
ExternalProject_Add(Fast-CDR
        GIT_REPOSITORY  https://github.com/eProsima/Fast-CDR.git
        GIT_TAG         v2.2.1
        CMAKE_ARGS
        -DCMAKE_INSTALL_PREFIX=${CMAKE_CURRENT_BINARY_DIR}/install
        -DBUILD_SHARED_LIBS=ON
)

# Fast-DDS
ExternalProject_Add(Fast-DDS
        GIT_REPOSITORY  https://github.com/eProsima/Fast-DDS.git
        GIT_TAG         v2.13.4
        DEPENDS         foonathan_memory_vendor Fast-CDR
        CMAKE_ARGS
        -DCMAKE_INSTALL_PREFIX=${CMAKE_CURRENT_BINARY_DIR}/install
        -DCMAKE_FIND_ROOT_PATH=${CMAKE_CURRENT_BINARY_DIR}/install
        -DBUILD_SHARED_LIBS=ON
        -DTHIRDPARTY_fastcdr=OFF
        -DTHIRDPARTY_Asio=FORCE
        -DTHIRDPARTY_TinyXML2=FORCE
        -DTHIRDPARTY_android-ifaddrs=FORCE
        -DSHM_TRANSPORT_DEFAULT=OFF
)

# あなたのネイティブライブラリ
add_library(native-lib SHARED publisher.cpp)
target_include_directories(native-lib PRIVATE ${CMAKE_CURRENT_BINARY_DIR}/install/include)
target_link_libraries(native-lib ${CMAKE_CURRENT_BINARY_DIR}/install/lib/libfastrtps.so ${CMAKE_CURRENT_BINARY_DIR}/install/lib/libfastcdr.so)
add_dependencies(native-lib Fast-DDS)