import React from 'react';
import {
  NativeModules,
  Button,
  StyleSheet,
  View
} from 'react-native';

export default function AdminStart ({navigation}) {
  const activityStarter = NativeModules.ActivityStarter;

  const createBook = function () {
    navigation.navigate('CreateBook', navigation);
  }

  const viewBooks = function () {
    activityStarter.navigateToViewBooks();
  }

  const logout = function () {
    activityStarter.logout();
  }

  return (
    <View>
        <View style={styles.buttonContainer}>  
          <Button    
              title="Add new Book"
              color="#009933"
              onPress={createBook}
          />  
        </View>  
        <View style={styles.buttonContainer}>  
          <Button  
              title="View"  
              color="#009933"
              onPress={viewBooks}
          />  
        </View>

        <View style={styles.buttonContainer}>  
          <Button  
              title="Logout"  
              color="#009933"
              onPress={logout} 
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
  buttonContainer: {  
      margin: 50,
  }
});
