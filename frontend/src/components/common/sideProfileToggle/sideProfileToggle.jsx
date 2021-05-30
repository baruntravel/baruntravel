import React, { useCallback, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import SideMyProfile from "../sideMyProfile/sideMyProfile";
import styles from "./sideProfileToggle.module.css";
import { Drawer } from "antd";

const SideProfileToggle = (props) => {
  const [openSideProfile, setOpenSideProfile] = useState(false);
  const onOpenSideProfile = useCallback(() => {
    setOpenSideProfile(true);
  }, []);
  const onCloseSideProfile = useCallback(() => {
    setOpenSideProfile(false);
  }, []);
  return (
    <>
      <div className={styles.menu}>
        <FontAwesomeIcon onClick={onOpenSideProfile} icon={faBars} size="2x" />
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

export default SideProfileToggle;
