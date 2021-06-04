import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faRoute, faHome, faHeart, faUser } from "@fortawesome/free-solid-svg-icons";
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
      <FontAwesomeIcon icon={faSearch} size="2x" onClick={searchClicked} />
      <FontAwesomeIcon icon={faRoute} size="2x" onClick={routeClicked} />
      <FontAwesomeIcon icon={faHome} size="2x" onClick={homeClicked} />
      <FontAwesomeIcon icon={faHeart} size="2x" onClick={heartClicked} />
      <FontAwesomeIcon icon={faUser} size="2x" onClick={userClicked} />
    </div>
  );
};

export default Navbar;
