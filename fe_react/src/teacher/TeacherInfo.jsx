import './teacherinfo.css'
import '../assets/style.css'
import MainNavbar from '../homepage/MainNavbar'
import Description from './Description';
import TeacherList from './TeacherList';
function TeacherInfo() {
    return (
        <>
            <div className="main">
                <MainNavbar />
            </div>
            <div className="header-info">
                <h1>Thông tin giảng viên</h1>
            </div>
            <div className="display-content">
                <Description />
                <TeacherList />
            </div>
        </>
    );
}

export default TeacherInfo