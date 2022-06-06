echo off
chcp 65001
taskkill /fi "imagename eq nginx.exe" /f
echo 已尝试关闭所有在运行的 Nginx ~！
pause