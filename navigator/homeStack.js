import { createStackNavigator, CreateStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';
import AdminStart from '../screens/adminstart';
import CreateBook from '../screens/createbook';
import Login from '../screens/login';

const screens = {
    Login: {
        screen: Login
    },
    AdminStart: {
        screen: AdminStart
    },
    CreateBook: {
        screen: CreateBook
    }
}

const HomeStack = createStackNavigator(screens);

export default createAppContainer(HomeStack);
