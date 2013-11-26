#!/bin/bash

path=`dirname $0`

BUILD_DIR=$1
PLUGIN_NAME=$2
BUILD_NUM=$3
PRODUCT=sdk

if [ ! "$BUILD_NUM" ]
then
	BUILD_NUM=2013.1128
fi

#
# Checks exit value for error
# 
checkError() {
    if [ $? -ne 0 ]
    then
        echo "Exiting due to errors (above)"
        exit -1
    fi
}

# 
# Canonicalize relative paths to absolute paths
# 
pushd $path > /dev/null
dir=`pwd`
path=$dir
popd > /dev/null


#
# OUTPUT_DIR
# 
OUTPUT_DIR=$BUILD_DIR/$PRODUCT

# Clean build
if [ -e "$OUTPUT_DIR" ]
then
	rm -rf "$OUTPUT_DIR"
fi

# Plugins
OUTPUT_PLUGINS_DIR=$OUTPUT_DIR/plugins/$BUILD_NUM
OUTPUT_DIR_IOS=$OUTPUT_PLUGINS_DIR/iphone
OUTPUT_DIR_IOS_SIM=$OUTPUT_PLUGINS_DIR/iphone-sim
OUTPUT_DIR_MAC=$OUTPUT_PLUGINS_DIR/mac-sim
OUTPUT_DIR_ANDROID=$OUTPUT_PLUGINS_DIR/android
OUTPUT_DIR_WIN32=$OUTPUT_PLUGINS_DIR/win32-sim

# Docs
OUTPUT_DIR_DOCS=$OUTPUT_DIR/docs

# Samples
OUTPUT_DIR_SAMPLES=$OUTPUT_DIR/samples

# Create directories
mkdir "$OUTPUT_DIR"
checkError

mkdir -p "$OUTPUT_DIR_IOS"
checkError

mkdir -p "$OUTPUT_DIR_IOS_SIM"
checkError

mkdir -p "$OUTPUT_DIR_MAC"
checkError

mkdir -p "$OUTPUT_DIR_ANDROID"
checkError

mkdir -p "$OUTPUT_DIR_WIN32"
checkError

mkdir -p "$OUTPUT_DIR_SAMPLES"
checkError

#
# Build
#

echo "------------------------------------------------------------------------"
echo "[ios]"
cd "$path/ios"
	./build.sh "$OUTPUT_DIR_IOS" $PLUGIN_NAME
	checkError

	cp -v metadata.lua "$OUTPUT_DIR_IOS"
	checkError

	cp -rv "$OUTPUT_DIR_IOS/" "$OUTPUT_DIR_IOS_SIM"

	# Remove i386 from ios build
	find "$OUTPUT_DIR_IOS" -name \*.a | xargs -n 1 -I % lipo -remove i386 % -output %

	# Remove armv7 from ios-sim build
	find "$OUTPUT_DIR_IOS_SIM" -name \*.a | xargs -n 1 -I % lipo -remove armv7 % -output %
cd -

# echo "------------------------------------------------------------------------"
# echo "[mac]"
# cd "$path/mac"
# 	./build.sh "$OUTPUT_DIR_MAC" $PLUGIN_NAME
# 	checkError
# cd -

echo "------------------------------------------------------------------------"
echo "[android]"
cd "$path/android"
	export OUTPUT_PLUGIN_DIR_ANDROID="$OUTPUT_DIR_ANDROID"
	./build.plugin.sh
	checkError
cd -

# echo "------------------------------------------------------------------------"
# echo "[win32]"
# cd "$path/shared"
# 	cp -v *.lua "$OUTPUT_DIR_WIN32"
# 	checkError
# cd -

echo "------------------------------------------------------------------------"
echo "[docs]"
cp -vrf "$path/docs" "$OUTPUT_DIR"
checkError

echo "------------------------------------------------------------------------"
echo "[samples]"
cp -vrf "$path/Corona/" "$OUTPUT_DIR_SAMPLES"
checkError

echo "------------------------------------------------------------------------"
echo "Generating plugin zip"
ZIP_FILE=$BUILD_DIR/${PRODUCT}-${PLUGIN_NAME}.zip
cd "$OUTPUT_DIR"
	zip -rv "$ZIP_FILE" *
cd -

echo "------------------------------------------------------------------------"
echo "Plugin build succeeded."
echo "Zip file located at: '$ZIP_FILE'"
