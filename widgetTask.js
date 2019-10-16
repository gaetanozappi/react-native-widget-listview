import { NativeModules, ToastAndroid } from 'react-native';
import bgTimer from 'react-native-background-timer';
const { BackgroundTaskBridge } = NativeModules;

export default async function widgetTask () {
  bgTimer.setTimeout(() => {
    fetch('https://www.simplifiedcoding.net/demos/marvel/')
    .then((response) => response.json())
    .then((responseJson) => {
      synchronizeWidget(responseJson)
    }).catch((error) => {
      console.error(error);
    });
  }, 0);
}

export function synchronizeWidget (a) {
  ToastAndroid.show(`Initializing...`, ToastAndroid.SHORT);
  BackgroundTaskBridge.initializeWidgetBridge(a);
}
