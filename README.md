
# react-native-native-audio-recorder

A React Native module which serves with a native module for audio recording, and two native ui components - one rendering a feedback plot during recording and another rendering the waveform of the recorded file.

## Getting started
1. Create a new React Native project with:  
`$ react-native init ProjectName`
    
2. Head into the project folder:  
`$ cd ProjectName`

3. Since we have not published this project to the NPM registry yet, you have to install the project directly from GitHub:  
`$ npm install audvice/react-native-audio-recorder#develop --save`

4. Run the following command to add the package to your project:  
`$ npm react-native link`

5. Open the project in the ios folder:  
`ProjectName.xcodeproj`
    
6. Add at least one Swift file to your project, the compiler needs this to recognize the bridging   
    
7. Go to Project -> Target -> Build Settings -> Section "Search Paths" -> "Framework Search Paths" and add:  
`$(SRCROOT)/../node_modules/react-native-native-audio-recorder/ios/Frameworks`
  
8. Go to Project -> Target -> Info -> "Custom iOS Target Properties" -> hit the plus and add:  
`Privacy - Microphone Usage Description`
    
9. Optional - for better development experience: inhibit hundred of third-party warnings:  
a) add the following lines to package.json:
    ```
    "scripts": {
      "inhibit-third-party-warnings": "react-native-inhibit-warnings"
    }
    ```
    b) run `$ npm install --save-dev react-native-inhibit-warnings`  
    c) run `$ npm run inhibit-third-party-warnings`  

    
    
## Usage
```javascript
import { AudioRecorder, AudioPlayerPlot } from 'react-native-native-audio-recorder';
```

## Example

For usage see the full-working [example project ](https://github.com/audvice/react-native-audio-recorder-example-project)