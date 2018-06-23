import React from 'react';
import { StackNavigator } from 'react-navigation';
import Home from '../screens/home';
import Profile from '../screens/profile';
import Group from '../screens/group';

export const StackN = StackNavigator({
    Home: {
      screen: Home,
      navigationOptions: {
        header: null,
      }
    },
    Profile: {
      screen: Profile,
      navigationOptions: {
        header: null,
      }
    },
    Group: {
      screen: Group,
      navigationOptions: {
        header: null,
      }
    }
});
