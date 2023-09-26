import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import lightRoast from '../../assets/survey/lightRoast.png';
import mediumRoast from '../../assets/survey/mediumRoast.png';
import darkRoast from '../../assets/survey/darkRoast.png';
import unknownRoast from '../../assets/survey/unknownRoast.png';
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveRoasting } from 'store/surveySlice';
import { NextButton } from './SurveyButton';
import { RoastingTooltip } from 'components/Tooltip/survey';

export const RoastingSelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const dispatch = useDispatch<AppDispatch>();
  const [selectedLight, setSelectedLight] = useState(false);
  const [selectedMedium, setSelectedMedium] = useState(false);
  const [selectedDark, setSelectedDark] = useState(false);
  const [selectedUnknown, setSelectedUnknown] = useState(false);
  const [myRoast, setMyRoast] = useState(-1);

  // 데이터
  const data = [
    {
      src: lightRoast,
      label: '라이트 로스팅',
      isSelected: selectedLight,
      setIsSelected: setSelectedLight,
      num: 0.3,
      toolTipDescription: '강한 신맛이 나고 품종의 특성이 잘 나타납니다.'
    },
    {
      src: mediumRoast,
      label: '미디엄 로스팅',
      isSelected: selectedMedium,
      setIsSelected: setSelectedMedium,
      num: 0.6,
      toolTipDescription : '산뜻한 신맛이 나고 품종의 특성이 약하게 나타납니다.'
    },
    {
      src: darkRoast,
      label: '다크 로스팅',
      isSelected: selectedDark,
      setIsSelected: setSelectedDark,
      num: 0.9,
      toolTipDescription: '신맛이 약해지고 단맛과 쓴맛이 강해집니다.'
    },
    {
      src: unknownRoast,
      label: '잘 모르겠어요',
      isSelected: selectedUnknown,
      setIsSelected: setSelectedUnknown,
      num: 0,
    },
  ];
  // 로스팅 단계 이미지 클릭 시
  const handleRoastSelect = (
    num: number,
    isSelected: boolean,
    setIsSelected: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (myRoast === -1) {
      setMyRoast(num);
      setIsSelected(!isSelected);
    } else if (myRoast === num) {
      setMyRoast(-1);
      setIsSelected(!isSelected);
    } else {
      alert('하나만 선택해주세요');
    }
  };
  // 다음 버튼 클릭 시
  const handleRoastSubmit = () => {
    // 선택 안 했을 때
    if (myRoast === -1) {
      alert('선호하는 로스팅 단계를 하나 선택해주세요');
    }
    // 선택 했을 때 - 다음 페이지로 & 로스팅 정보 저장
    else {
      dispatch(addCurrentPage());
      dispatch(saveRoasting(myRoast));
    }
  };

  return (
    <div className="flex flex-col items-center gap-10 mt-10">
      {/* 설문 상단 */}
      <div className="flex flex-col items-center gap-2">
        <p>
          {survey.currentPage}/{survey.totalPage}
        </p>
        <div className="flex flex-row">
          <p className=" text-2xl font-bold">
            선호하는 로스팅 단계를 선택해주세요
          </p>
        </div>
        <p className="flex w-560px h-2.5 rounded-lg bg-process-bar">
          <span
            className={` botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-half-light`}
          ></span>
        </p>
      </div>
      {/* 설문 사진 */}
      <div className="flex flex-row gap-10">
        {data.map((item) => {
          const { src, label, isSelected, setIsSelected, num, toolTipDescription } = item;
          return (
            <div
              className={`w-64 h-60 flex flex-col items-center justify-end ${
                isSelected ? 'bg-select-img' : ''
              } rounded-xl`}
              key={num}
            >
              <img
                className={`w-48 h-48 origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
                src={src}
                onClick={() =>
                  handleRoastSelect(num, isSelected, setIsSelected)
                }
              />
              <p className='mb-2'>{label} 
              {toolTipDescription&&
                <RoastingTooltip label={toolTipDescription}/>
              }
              </p>
            </div>
          );
        })}
      </div>
      {/* 버튼 */}
      <NextButton handleClick={handleRoastSubmit} label='다음' />
    </div>
  );
};