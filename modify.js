import React, { useState } from 'react';
import {
  AppRegistry,
  NativeModules,
  Button,
  StyleSheet,
  TextInput,
  View
} from 'react-native';

import ToolbarAndroid from '@react-native-community/toolbar-android';

const HelloWorld = () => {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [year, setYear] = useState("");
  const [publisher, setPublisher] = useState("");
  const [cost, setCost] = useState("");
  const [noofCopies, setNoofCopies] = useState("");
  const activityStarter = NativeModules.ActivityStarter;

  return (
    <View style={styles.container}>
      <ToolbarAndroid style={styles.toolbar} title="Create Book"/>
      <TextInput
        style={styles.input}
        onChangeText={newText => setTitle(newText)}
        value={title}
        placeholder="Title"
      />
      <TextInput
        style={styles.input}
        onChangeText={newText => setAuthor(newText)}
        value={author}
        placeholder="Author"
      />
      <TextInput
        style={styles.input}
        onChangeText={newText => setYear(newText)}
        value={year}
        placeholder="Year"
      />
      <TextInput
        style={styles.input}
        onChangeText={newText => setPublisher(newText)}
        value={publisher}
        placeholder="Publisher"
      />
      <TextInput
        style={styles.input}
        onChangeText={newText => setCost(newText)}
        value={cost}
        placeholder="Cost"
      />
      <TextInput
        style={styles.input}
        onChangeText={newText => setNoofCopies(newText)}
        value={noofCopies}
        placeholder="noofCopies"
      />
      <View style={styles.buttonContainer}>
        <Button
          onPress={() => activityStarter.createBook(title, publisher, author, cost, year, noofCopies)}
          title='Create Book'
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
