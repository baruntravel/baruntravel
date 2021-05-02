import styles from "./navbar.module.css";
import Logo from "../logo/logo";
import { useCallback, useState } from "react";
import { Drawer } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import SideMyProfile from "../../sideMyProfile/sideMyProfile";

const Navbar = () => {
  const [openSideProfile, setOpenSideProfile] = useState(false);
  const onOpenSideProfile = useCallback(() => {
    setOpenSideProfile(true);
  }, []);
  const onCloseSideProfile = useCallback(() => {
    setOpenSideProfile(false);
  }, []);
  return (
    <>
      <div className={styles.navbarContainer}>
        <div className={styles.logo}>
          <Logo />
        </div>
        <div className={styles.menu}>
          <FontAwesomeIcon
            onClick={onOpenSideProfile}
            icon={faBars}
            size="2x"
          />
        </div>
      </div>
      <Drawer
        placement="right"
        closable={false}
        visible={openSideProfile}
        width={window.innerWidth > 768 ? "36vw" : "80vw"}
        bodyStyle={{ padding: 0 }}
        onClose={onCloseSideProfile}
      >
        <SideMyProfile />
      </Drawer>
    </>
  );
};

export default Navbar;
