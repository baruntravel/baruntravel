import { SearchOutlined, ShareAltOutlined, HomeOutlined, HeartOutlined, UserOutlined } from "@ant-design/icons";
import styles from "./navbar.module.css";
import { useHistory } from "react-router-dom";

const Navbar = () => {
  const history = useHistory();
  const searchClicked = () => history.push("/"); // 둘러보기 페이지
  const routeClicked = () => history.push("/routemaker"); // 루트 만들기 페이지
  const homeClicked = () => history.push("/");
  const heartClicked = () => history.push("/wishlist");
  const userClicked = () => history.push("/mypage");

  return (
    <div className={styles.container}>
      <SearchOutlined className={styles.icon} onClick={searchClicked} />
      <ShareAltOutlined className={styles.icon} onClick={routeClicked} />
      <HomeOutlined className={styles.icon} onClick={homeClicked} />
      <HeartOutlined className={styles.icon} onClick={heartClicked} />
      <UserOutlined className={styles.icon} onClick={userClicked} />

      {/* <FontAwesomeIcon icon={faSearch} size="2x" onClick={searchClicked} />
      <FontAwesomeIcon icon={faRoute} size="2x" onClick={routeClicked} />
      <FontAwesomeIcon icon={faHome} size="2x" onClick={homeClicked} />
      <FontAwesomeIcon icon={faHeart} size="2x" onClick={heartClicked} />
      <FontAwesomeIcon icon={faUser} size="2x" onClick={userClicked} /> */}
    </div>
  );
};

export default Navbar;
