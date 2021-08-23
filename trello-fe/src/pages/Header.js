import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../../src/style.css'
import '../App.css'
import { HouseDoor, PlusLg, PersonCircle } from 'react-bootstrap-icons';

const Header = () => {

    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-blue">

                <div className="col-sm">
                    <ul>
                        <li className="li-list-item center-nd">
                            <a href="/home"><HouseDoor color="white" size={50} /></a>
                        </li>
                    </ul>
                </div>
                <div className="col-sm header-title">
                    <h1>Trello</h1>
                </div>
                <div className="col-sm ">
                    <ul className="ul-right">
                        <li className="li-list-item">
                            <a href="/home"><PlusLg color="white" size={50} /></a>
                        </li>
                        <li className="li-list-item">
                            <a href="/home"><PersonCircle color="white" size={50} /></a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    )
}

export default Header;