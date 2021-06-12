import styles from "./header.module.css";
import Logo from "../logo/logo";
import SideProfileToggle from "../sideProfileToggle/sideProfileToggle";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";

const Header = ({ title, onBackHandler }) => {
  const history = useHistory();
  const isMain = window.location.pathname.split("/").pop(); // url 마지막 부분이 ID이다.

  return (
    <>
      <div className={styles.headerContainer}>
        <div className={styles.logo}>
          {isMain ? (
            <FontAwesomeIcon
              onClick={onBackHandler || history.goBack}
              icon={faChevronLeft}
              size="2x"
              className={styles.backIcon}
            />
          ) : (
            <Logo />
          )}
        </div>
        <div className={styles.title}>{title}</div>
        {/* <div className={styles.groupChat}>그룹 채팅 아이콘</div> */}
        <SideProfileToggle />
      </div>
    </>
  );
};

export default Header;
