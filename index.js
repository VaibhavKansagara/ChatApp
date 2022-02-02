import React, { useState } from 'react';
import { AppRegistry } from 'react-native';

import Navigator from './navigator/homeStack';

const HelloWorld = () => {

  return (
    <Navigator/>
  );
};

AppRegistry.registerComponent(
  'ReactAndroidApp',
  () => HelloWorld
);
