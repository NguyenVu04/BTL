import '../assets/style.css'
import './teacherinfo.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGraduationCap, faFileSignature } from '@fortawesome/free-solid-svg-icons';
function Description() {
    return (
        <div className="display-info">
            <div className="display-info__avt">
                <div className="ava_vananh"></div>
                <div className="info-name" style={{ margin: 'auto' }}>
                    <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faGraduationCap} /> </i>
                    <h2>TS. Nguyễn Vân Anh</h2>
                </div>
            </div>
            <div className="display-info__inf">
                <div className="info-full">
                    <div className="info-full__content">
                        <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faFileSignature} /> </i>
                        <p style={{ margin: 'auto 0px' }}>Họ và tên:</p>
                    </div>
                    <div className="info-full__content1"><p style={{ margin: '20px 20px' }}>Nguyễn Vân Anh</p></div>
                </div>
                <div className="info-full">
                    <div className="info-full__content">
                        <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faFileSignature} /> </i>
                        <p style={{ margin: 'auto 0px' }}>Chức vụ:</p>
                    </div>
                    <div className="info-full__content1"><p style={{ margin: '20px 20px' }}>Nguyễn Vân Anh</p></div>
                </div>
                <div className="info-full">
                    <div className="info-full__content">
                        <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faFileSignature} /> </i>
                        <p style={{ margin: 'auto 0px' }}>Bộ môn:</p>
                    </div>
                    <div className="info-full__content1"><p style={{ margin: '20px 20px' }}>Nguyễn Vân Anh</p></div>
                </div>
                <div className="info-full">
                    <div className="info-full__content">
                        <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faFileSignature} /> </i>
                        <p style={{ margin: 'auto 0px' }}>Email:</p>
                    </div>
                    <div className="info-full__content1"><p style={{ margin: '20px 20px' }}>Nguyễn Vân Anh</p></div>
                </div>
                <div className="info-full">
                    <div className="info-full__content">
                        <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faFileSignature} /> </i>
                        <p style={{ margin: 'auto 0px' }}>Điện thoại:</p>
                    </div>
                    <div className="info-full__content1"><p style={{ margin: '20px 20px' }}>Nguyễn Vân Anh</p></div>
                </div>
                <div className="info-full">
                    <div className="info-full__content">
                        <i style={{ margin: 'auto 5px' }}><FontAwesomeIcon icon={faFileSignature} /> </i>
                        <p style={{ margin: 'auto 0px' }}>Nghiên cứu khoa học:</p>
                    </div>
                </div>
                <div style={{ overflow: 'auto', height: '40%', padding: '0px 10px 10px 30px' }}>
                    <ul>
                        <li>Nghiên cứu xây dựng hệ thống cảnh báo sớm trượt lở đất sử dụng công nghệ GNSS khu vực cao nguyên phục vụ phát triển bền vững kinh tế xã hội</li>
                        <li>Nghiên cứu phân vùng ổn định khu vực bờ sông đồng bằng Sông Cửu Long và các giải pháp bảo vệ bờ </li>
                        <li>Đánh giá tác động môi trường của các dự án phát triển du lịch, điện gió và điện mặt trời vùng đới bờ biển khu vực Khánh Hòa, Bình Thuận, Vũng Tàu và  biển Tây Nam Bộ </li>
                        <li>Xây dựng hệ thống cảnh báo sớm ao xoáy /dòng rip khu vực bãi tắm du lịch khu vực biển Vũng Tàu, Nha Trang</li>
                        <li>Nghiên cứu xây dựng kho ngầm dự trữ năng lượng (dầu, khí hoá lỏng) Dung Quất, Bà Rịa Vũng Tàu</li>
                        <li>Nghiên cứu xây dựng kho ngầm dự trữ an ninh lương thực, thực phẩm (cá, thịt, rau củ quả) vùng Lâm Đồng</li>
                        <li>Nghiên cứu phát triển bền vững các khu vực phát triển kinh tế du lịch đới bờ biển và vùng cao nguyên</li>
                        <li>Nghiên cứu xây dựng bản đồ phân vùng nhạy cảm môi trường khu vực đới bờ biển Việt Nam</li>
                    </ul>
                </div>
            </div>
        </div>
    );
}
export default Description