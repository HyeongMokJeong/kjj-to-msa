package com.kjj.store.service

import com.kjj.store.client.ImageClient
import com.kjj.store.dto.*
import com.kjj.store.entity.Store
import com.kjj.store.entity.StoreState
import com.kjj.store.exception.CantFindByIdException
import com.kjj.store.exception.DataAlreadyExistsException
import com.kjj.store.repository.StoreRepository
import com.kjj.store.repository.StoreStateRepository
import com.kjj.store.util.DateTool
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.LocalDate

@Service
class StoreService(
    val imageClient: ImageClient,
    val storeRepository: StoreRepository,
    val storeStateRepository: StoreStateRepository,
    val dateTool: DateTool,
) {
    companion object {
        const val FINAL_ID: Long = 1L
    }

    @Transactional(readOnly = true)
    fun isSetting(): StoreDto? {
        val store = storeRepository.findById(FINAL_ID).orElse(null) ?: return null
        return StoreDto.from(store)
    }

    @Transactional(readOnly = true)
    fun getStoreData(): StoreDto {
        return StoreDto.from(storeRepository.findById(FINAL_ID).orElseThrow {
            CantFindByIdException(
                """
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : $FINAL_ID
                """
            )
        })
    }


    @Transactional
    fun setSetting(dto: StoreSettingDto): StoreDto {
        if (storeRepository.existsById(FINAL_ID)) throw DataAlreadyExistsException(
            """
                이미 ID가 1인 Store 데이터가 존재합니다.
                dto : $dto
                """
        )
        return StoreDto.from(storeRepository.save(Store.of(FINAL_ID, dto)))
    }


    @Transactional
    fun setStoreImage(file: MultipartFile) {
        val store = storeRepository.findById(FINAL_ID).orElseThrow {
            CantFindByIdException(
                """
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : $FINAL_ID
                """
            )
        }
        val uploadDir = "img/store"
        if (store.image == null) {
            store.image = imageClient.uploadImage(file, uploadDir)
        } else imageClient.updateImage(
            file,
            store.image!!
        )
    }

    @Transactional
    fun updateStoreTitle(dto: StoreTitleDto): StoreDto {
        val store = storeRepository.findById(FINAL_ID).orElseThrow {
            CantFindByIdException(
                """
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : $FINAL_ID
                """
            )
        }
        store.name = dto.name
        return StoreDto.from(store)
    }


    @Transactional
    fun updateStoreInfo(dto: StoreInfoDto): StoreDto {
        val store = storeRepository.findById(FINAL_ID).orElseThrow {
            CantFindByIdException(
                """
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : $FINAL_ID
                """
            )
        }
        store.info = dto.info
        return StoreDto.from(store)
    }


    @Transactional
    fun setOff(dto: StoreOffDto, year: Int, month: Int, day: Int): StoreStateDto {
        val date: LocalDate = dateTool.makeLocalDate(year, month, day)
        val storeState: StoreState = storeStateRepository.findByDate(date)
            ?: storeStateRepository.save(
                StoreState.createNewOffStoreState(
                    storeRepository.getReferenceById(FINAL_ID),
                    date,
                    dto
                ))
        storeState.setOff(dto)
        return StoreStateDto.from(storeState)
    }


    @Transactional(readOnly = true)
    fun getClosedDays(year: Int, month: Int): List<StoreStateDto> {
        val start: LocalDate = dateTool.makeLocalDate(year, month, 1)
        val end: LocalDate = dateTool.makeLocalDate(year, month, dateTool.getLastDay(year, month))

        return storeStateRepository.findAllByDateBetween(start, end)
            .map { StoreStateDto.from( it ) }
    }
}