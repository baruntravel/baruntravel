import styles from "./navbar.module.css";
import Logo from "../logo/logo";
import Menu from "../menu/menu";
import { useState } from "react";
import { Drawer } from "antd";
import SideMyProfile from "../../sideMyProfile/sideMyProfile";

const Navbar = () => {
  const [openSideProfile, setOpenSideProfile] = useState(false);
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
      <Drawer
        placement="right"
        closable={false}
        visible={true}
        width={window.innerWidth > 768 ? "36vw" : "80vw"}
        bodyStyle={{ padding: 0 }}
      >
        <SideMyProfile />
      </Drawer>
    </>
  );
};

export default Navbar;
