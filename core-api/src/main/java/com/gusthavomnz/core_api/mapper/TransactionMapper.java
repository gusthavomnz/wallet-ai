package com.gusthavomnz.core_api.mapper;

import com.gusthavomnz.core_api.dto.transaction.CreateTransactionRequest;
import com.gusthavomnz.core_api.dto.transaction.TransactionResponse;
import com.gusthavomnz.core_api.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Transaction toEntity(CreateTransactionRequest request);

    @Mapping(target = "userId", source = "transaction.user.id")
    @Mapping(target = "categoryId", source = "transaction.category.id")
    @Mapping(target = "imageUrl", source = "imageUrl")
    TransactionResponse toResponse(Transaction transaction, String imageUrl);
}
