echo off
chcp 65001
taskkill /fi "imagename eq nginx.exe" /f
start nginx.exe
echo 已尝试启动/重启 Nginx~！
pause