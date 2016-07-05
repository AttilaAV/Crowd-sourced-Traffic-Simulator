#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/TrafficAnalytics.pb.o \
	${OBJECTDIR}/gps.o \
	${OBJECTDIR}/main.o \
	${OBJECTDIR}/mqttcomm.o \
	${OBJECTDIR}/video_processing.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=`pkg-config --cflags --libs protobuf --libs opencv` -lpthread -lpaho-mqtt3c 
CXXFLAGS=`pkg-config --cflags --libs protobuf --libs opencv` -lpthread -lpaho-mqtt3c 

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/zybo_tac

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/zybo_tac: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/zybo_tac ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/TrafficAnalytics.pb.o: TrafficAnalytics.pb.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -std=c++11 -lpthread `pkg-config --cflags --libs protobuf --libs opencv` -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/TrafficAnalytics.pb.o TrafficAnalytics.pb.cc

${OBJECTDIR}/gps.o: gps.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.c) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/gps.o gps.c

${OBJECTDIR}/main.o: main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -std=c++11 -lpthread `pkg-config --cflags --libs protobuf --libs opencv` -std=c++11 -lpthread -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.cpp

${OBJECTDIR}/mqttcomm.o: mqttcomm.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.c) -g -lpaho-mqtt3c -lpthread -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/mqttcomm.o mqttcomm.c

${OBJECTDIR}/video_processing.o: video_processing.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -std=c++11 -lpthread `pkg-config --cflags --libs protobuf --libs opencv` `pkg-config --cflags --libs opencv` -std=c++11 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/video_processing.o video_processing.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/zybo_tac

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc