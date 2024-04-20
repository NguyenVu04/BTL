import './mainpage.css'
import '../assets/style.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
function MenuCourses () {
 return (
    <div className="menu-courses menu-effect">
      <a href="#course">
        Khóa học của tôi
        <i> <FontAwesomeIcon icon={faCaretDown}/></i>
      </a>
      <ul className="subnav">
        <li><a href="#">Danh sách khóa học</a></li>
        <li><a href="#">Đăng kí mới</a></li>
      </ul>
      <div className="underline"></div>
    </div>
 );
};

export default MenuCourses;
