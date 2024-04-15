import './mainpage.css'
import '../assets/style.css'
import HCMUT_official_logo from '../assets/img/HCMUT_official_logo.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCaretDown, faBell, faUserCircle } from '@fortawesome/free-solid-svg-icons';

import TeacherMenu from './TeacherMenu';
import StudentMenu from './StudentMenu';
function MainPage() {
    return (
        <div className="main">
            <div id="header">
                <div id="nav">
                    <div className="nav__logo">
                        <a href="#home">
                            <img src={HCMUT_official_logo} alt="Logo" />
                        </a>
                    </div>

                    <div className="nav__menu">
                        <div className="menu-home menu-effect">
                            <a href="#home">Trang chủ</a>
                        </div>
                        <TeacherMenu/>
                        <StudentMenu/>
                    </div>

                    <div className="nav__icon">
                        <div className="noti-icon">
                            <i><FontAwesomeIcon icon={faBell} size='sm'/></i>
                        </div>
                        <div className="ava-icon">
                            <i><FontAwesomeIcon icon={faUserCircle} size='sm'/></i>
                            <ul className="subnav">
                                <li><a href="#">Đăng nhập</a></li>
                                <li><a href="#">Đăng xuất</a></li>
                                <li><a href="#">Đổi mật khẩu</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div id="slideshow">
                <div className="slide-wrapper">
                    <div className="slide img1"></div>
                    <div className="slide img2"></div>
                    <div className="slide img3"></div>
                </div>
            </div>
        </div>
    );
}

export default MainPage