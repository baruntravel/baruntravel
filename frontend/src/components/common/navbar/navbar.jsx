import styles from "./navbar.module.css";
import Logo from "../logo/logo";
import Menu from "../menu/menu";

const Navbar = () => {
  return (
    <>
      <div className={styles.navbarContainer}>
        <div className={styles.logo}>
          <Logo />
        </div>
        <div className={styles.menu}>
          <Menu />
        </div>
      </div>
    </>
  );
};

export default Navbar;
