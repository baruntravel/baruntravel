import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faRoute, faHome, faHeart, faUser } from "@fortawesome/free-solid-svg-icons";
import styles from "./navbar.module.css";

const Navbar = () => {
  return (
    <div className={styles.container}>
      <FontAwesomeIcon icon={faSearch} size="2x" className={styles.icon} />
      <FontAwesomeIcon icon={faRoute} size="2x" className={styles.icon} />
      <FontAwesomeIcon icon={faHome} size="2x" className={styles.icon} />
      <FontAwesomeIcon icon={faHeart} size="2x" className={styles.icon} />
      <FontAwesomeIcon icon={faUser} size="2x" className={styles.icon} />
    </div>
  );
};

export default Navbar;
