@echo off&setlocal enabledelayedexpansion
call mvn clean package -Dmaven.test.skip=true
set index=0
set version=
if not exist "pom.xml" goto end
for /f "tokens=1,* delims=>" %%i in ('findstr "<version>" "pom.xml"') do (
	set "head=%%i" & set "iver=%%j" & set /a "index = !index!+1" & if "!index!" EQU "2" (set "version=!iver!")
	)
set version=!version:~0,-10!
set folder=!version:-SNAPSHOT=!
echo ^open 192.168.0.128^ > ftp.cfg
echo kaifa>> ftp.cfg
echo mainbo.kf>> ftp.cfg
echo ^cd /标准产品/标准教研/^ >> ftp.cfg
echo ^mkdir !folder!^ >> ftp.cfg
echo ^cd !folder!^ >> ftp.cfg
echo ^lcd %~dp0\..\jy-web\target^ >> ftp.cfg
echo ^put jy-web-!version!.war^ >> ftp.cfg
echo ^lcd %~dp0\..\jy-background\target^ >> ftp.cfg
echo ^put jy-background-!version!.war^ >> ftp.cfg
echo bye >> ftp.cfg

ftp -s:ftp.cfg
echo -------------finished package and upload to ftp-------
del /f ftp.cfg
goto over
:end
echo -------------finished package and not upload to ftp-------
:over

pause