import styles from "./navbar.module.css";
import Logo from "../logo/logo";

const Navbar = ({ title }) => {
  return (
    <>
      <div className={styles.navbarContainer}>
        <div className={styles.logo}>
          <Logo />
        </div>
        <div className={styles.profileWrap}></div>
      </div>
    </>
  );
};

export default Navbar;
