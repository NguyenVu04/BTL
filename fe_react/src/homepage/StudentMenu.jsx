import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';

function StudentMenu() {
    return (
        <div className="menu-student menu-effect">
            <a href="#student">
                Thông tin sinh viên 
                <i> <FontAwesomeIcon icon={faCaretDown} /></i>
            </a>
            <ul className="subnav">
                <li><a href="#">Thông tin cá nhân</a></li>
                <li><a href="#">Tiến trình học tập</a></li>
                <li><a href="#">Kết quả đánh giá</a></li>
            </ul>
            <div className="underline"></div>
        </div>
    );
}
export default StudentMenu