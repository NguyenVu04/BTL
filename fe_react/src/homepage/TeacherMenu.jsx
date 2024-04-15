import './mainpage.css'
import '../assets/style.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCaretDown, faBell, faUserCircle } from '@fortawesome/free-solid-svg-icons';
function TeacherMenu() {
    return (
        <div className="menu-teacher menu-effect">
            <a href="#teacher">
                Thông tin giảng viên
                <i> <FontAwesomeIcon icon={faCaretDown} /></i>
            </a>
            <ul className="subnav">
                <li><a href="#">Thông tin cá nhân</a></li>
                <li><a href="#">Lịch trình giảng dạy</a></li>
                <li><a href="#">Danh sách lớp</a></li>
            </ul>
            <div className="underline"></div>
        </div>
    );
}

export default TeacherMenu