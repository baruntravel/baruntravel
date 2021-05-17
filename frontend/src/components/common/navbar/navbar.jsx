import styles from "./navbar.module.css";
import Logo from "../logo/logo";
import { Drawer } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import SideMyProfile from "../../sideMyProfile/sideMyProfile";
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
