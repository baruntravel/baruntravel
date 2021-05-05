import styles from "./navbar.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft, faShoppingCart } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";
import { useState, useCallback } from "react";
import SideMyProfile from "../../../components/sideMyProfile/sideMyProfile";
import { Drawer } from "antd";

const Navbar = () => {
  const [openSideProfile, setOpenSideProfile] = useState(false);
  const onOpenSideProfile = useCallback(() => {
    setOpenSideProfile(true);
  }, []);
  const onCloseSideProfile = useCallback(() => {
    setOpenSideProfile(false);
  }, []);
  const history = useHistory();
  return (
    <div className={styles.container}>
      <div className={styles.backButton} onClick={() => history.goBack()}>
        <FontAwesomeIcon icon={faChevronLeft} color="black" size="lg" />
      </div>
      <div className={styles.cartButton}>
        <FontAwesomeIcon onClick={onOpenSideProfile} icon={faShoppingCart} color="black" size="lg" />
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
    </div>
  );
};

export default Navbar;
