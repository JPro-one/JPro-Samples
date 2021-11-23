### PWA

The sample will only work, when the page is served with HTTPS, localhost or an ip of the form 127.x.x.x.

The following files are important:
 - The index.html contains changes to track whether the App is already installed.
 - The PWAUtil file contains the logic to check whether the app is installed and to initiate the install.
 - PWAApp shows how to use PWAUtil.
 - The manifest.json, sw.js and the images defines the PWA. Especially the manifest.json can be modified to change the PWA.