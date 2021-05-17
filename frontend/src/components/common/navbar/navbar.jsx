import styles from "./navbar.module.css";
import Logo from "../logo/logo";
import SideProfileToggle from "../sideProfileToggle/sideProfileToggle";

const Navbar = () => {
  return (
    <>
      <div className={styles.navbarContainer}>
        <div className={styles.logo}>
          <Logo />
        </div>
        <SideProfileToggle />
      </div>
    </>
  );
};

export default Navbar;
