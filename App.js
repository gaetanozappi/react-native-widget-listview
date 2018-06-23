import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';

export default class App extends Component<{}> {

  render() {
    const { navigationKey, medication } = this.props;
    console.log(this.props);
    if (navigationKey === 'MedicationScreen') {
      return (
        <View style={styles.container}>
          <Text style={styles.instructions}>
            I am in medication screen !
          </Text>
          <Text style={styles.instructions}>
            {medication.medId}
          </Text>
          <Text style={styles.instructions}>
            {medication.medName}
          </Text>
        </View>
      );
    }
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit App.js and widgetTask.js
        </Text>
        <Text style={styles.instructions}>
          Edit your native code and define the business logic in
          javascript
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
