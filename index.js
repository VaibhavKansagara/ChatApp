import React from 'react';
import {
  AppRegistry,
  NativeModules,
  Button,
  StyleSheet,
  Text,
  View
} from 'react-native';

const HelloWorld = () => {
  const activityStarter = NativeModules.ActivityStarter;
  const onPress = () => {
    console.log('We will invoke the native module here!');
  };

  return (
    <View style={styles.container}>
      <Text style={styles.hello}>Hello, World</Text>
      <Button style={styles.buttonContainer}
            onPress={() => activityStarter.navigateToExample()}
            title='Start'/>
    </View>
  );
};

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center'
  },
  buttonContainer: {
    height: 300,
    width: "80%",
    justifyContent: 'space-between',
    marginTop: 30,
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10
  }
});

AppRegistry.registerComponent(
  'ReactAndroidApp',
  () => HelloWorld
);
