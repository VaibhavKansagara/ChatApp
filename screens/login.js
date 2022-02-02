import React, { useState } from 'react';
import {
  NativeModules,
  Button,
  StyleSheet,
  TextInput,
  View
} from 'react-native';

import ToolbarAndroid from '@react-native-community/toolbar-android';

export default function Login ({navigation}) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const activityStarter = NativeModules.ActivityStarter;

  const onPress = async function () {
    await activityStarter.loginUser(email, password);
    navigation.navigate('AdminStart', navigation);
  }

  return (
    <View style={styles.container}>
      <ToolbarAndroid style={styles.toolbar} title="Login"/>
      <TextInput
        style={styles.input}
        onChangeText={newText => setEmail(newText)}
        value={email}
        placeholder="Email"
      />
      <TextInput
        style={styles.input}
        onChangeText={newText => setPassword(newText)}
        value={password}
        placeholder="Password"
      />
      <View style={styles.buttonContainer}>
        <Button
          title='Login'
          onPress={onPress}
        />
      </View>
    </View>
  );
};

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  toolbar: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    height: 40,
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    backgroundColor: 'grey'
  },
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
  buttonContainer: {
    flexDirection: 'row', 
    height: 50, 
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 5,
    elevation:3
  }
});
