<template>
  <div>
    <h2 id="page-heading" data-cy="ChannelHeading">
      <span v-text="$t('exchangeApp.channel.home.title')" id="channel-heading">Channels</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('exchangeApp.channel.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ChannelCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-channel"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('exchangeApp.channel.home.createLabel')"> Create a new Channel </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && channels && channels.length === 0">
      <span v-text="$t('exchangeApp.channel.home.notFound')">No channels found</span>
    </div>
    <div class="table-responsive" v-if="channels && channels.length > 0">
      <table class="table table-striped" aria-describedby="channels">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('exchangeApp.channel.name')">Name</span></th>
            <th scope="row"><span v-text="$t('exchangeApp.channel.homeLink')">Home Link</span></th>
            <th scope="row"><span v-text="$t('exchangeApp.channel.secretKey')">Secret Key</span></th>
            <th scope="row"><span v-text="$t('exchangeApp.channel.isActive')">Is Active</span></th>
            <th scope="row"><span v-text="$t('exchangeApp.channel.client')">Client</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="channel in channels" :key="channel.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ChannelView', params: { channelId: channel.id } }">{{ channel.id }}</router-link>
            </td>
            <td>{{ channel.name }}</td>
            <td>{{ channel.homeLink }}</td>
            <td>{{ channel.secretKey }}</td>
            <td>{{ channel.isActive }}</td>
            <td>
              <div v-if="channel.client">
                <router-link :to="{ name: 'ClientView', params: { clientId: channel.client.id } }">{{ channel.client.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ChannelView', params: { channelId: channel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ChannelEdit', params: { channelId: channel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(channel)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="exchangeApp.channel.delete.question" data-cy="channelDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-channel-heading" v-text="$t('exchangeApp.channel.delete.question', { id: removeId })">
          Are you sure you want to delete this Channel?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-channel"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeChannel()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./channel.component.ts"></script>
