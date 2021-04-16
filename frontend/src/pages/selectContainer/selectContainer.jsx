import styles from "./selectContainer.module.css";
import SelectArea from "./selectArea/selectArea";
import SelectRoute from "./selectRoute/selectRoute";

const SelectContainer = () => {
  return (
    <div className={styles.container}>
      <SelectArea />
      <SelectRoute />
    </div>
  );
};

export default SelectContainer;
