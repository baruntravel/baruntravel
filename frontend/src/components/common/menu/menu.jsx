import styles from "./menu.module.css";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { Drawer } from "antd";

const Menu = () => {
  const [visible, setVisible] = useState(false);
  const showDrawer = () => setVisible(true);
  const onClose = () => setVisible(false);

  return (
    <>
      <FontAwesomeIcon icon={faBars} size="2x" onClick={showDrawer} />
      <Drawer
        placement="right"
        closable={true}
        onClose={onClose}
        visible={visible}
        width={window.innerWidth > 768 ? "36vw" : "50vw"}
        bodyStyle={{
          backgroundColor: "#ebecec",
          padding: 0,
        }}
        zIndex={1004}
      ></Drawer>
    </>
  );
};

export default Menu;
