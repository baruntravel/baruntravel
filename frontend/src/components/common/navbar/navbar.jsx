import styles from "./navbar.module.css";
import Logo from "../logo/logo";

const Navbar = ({ title }) => {
  return (
    <>
      <div className={styles.navbarContainer}>
        <Logo />
        <h1 className={styles.navbarTitle}>{title}</h1>
        <div className={styles.profileWrap}></div>
      </div>
    </>
  );
};

export default Navbar;
