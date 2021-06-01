import styles from "./header.module.css";
import Logo from "../logo/logo";
import SideProfileToggle from "../sideProfileToggle/sideProfileToggle";

const Header = ({ onBackHandler }) => {
  const isMain = window.location.pathname.split("/").pop(); // url 마지막 부분이 ID이다.
  return (
    <>
      <div className={styles.headerContainer}>
        <div className={styles.logo}>{isMain ? <div>hi</div> : <Logo />}</div>
        <div className={styles.groupChat}>그룹 채팅 아이콘</div>
        <SideProfileToggle />
      </div>
    </>
  );
};

export default Header;
