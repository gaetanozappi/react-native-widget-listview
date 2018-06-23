import { AppRegistry } from 'react-native';
import App from './app/index';
import WidgetTask from './widgetTask';
AppRegistry.registerComponent('androidWidgetPoc', () => App);
AppRegistry.registerHeadlessTask('WidgetTask', () => WidgetTask);
