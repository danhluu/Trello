import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Board from '../pages/Board';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Register from '../pages/Register';


class HomeUrl extends Component {
    render() {
        return (
            <BrowserRouter>
                <Switch >
                    <Route exact path="/" component={Login}></Route>
                    <Route exact path="/register" component={Register}></Route>
                    <Route exact path="/home/" component={Home}></Route>
                    <Route exact path="/board/:id" component={Board}></Route>
                </Switch>
            </BrowserRouter>
        );
    }
}


export default HomeUrl;