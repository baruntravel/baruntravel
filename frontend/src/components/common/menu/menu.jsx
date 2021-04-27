import styles from "./menu.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";

const Menu = () => {
  return (
    <div>
      <FontAwesomeIcon icon={faBars} size="2x" />
    </div>
  );
};

export default Menu;
