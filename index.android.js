import { AppRegistry } from 'react-native';
import App from './app/index';
import widgetTask from './widgetTask';
AppRegistry.registerHeadlessTask('widgetTask', () => widgetTask);
AppRegistry.registerComponent('androidWidgetPoc', () => App);
